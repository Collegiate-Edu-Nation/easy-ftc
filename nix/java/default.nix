# SPDX-FileCopyrightText: Collegiate Edu-Nation
# SPDX-License-Identifier: GPL-3.0-or-later

{ pkgs }:

let
  tools = pkgs.runCommand "java-cert-tools" { nativeBuildInputs = [ pkgs.jdk21 ]; } ''
    mkdir -p "$out"
    cp ${./CheckCerts.java} CheckCerts.java
    cp ${./RemoveCerts.java} RemoveCerts.java
    javac --release 17 -d "$out" CheckCerts.java RemoveCerts.java
    cp CheckCerts.java "$out/CheckCerts.java"
  '';

  roots = [
    "emSign ECC Root CA - G3"
    "AAA Certificate Services"
    "emSign Root CA - G1"
    "emSign Root CA - G2"
  ];

  secure =
    runtime:
    let
      homeSuffix = pkgs.lib.removePrefix (toString runtime) (toString runtime.home);
      result =
        pkgs.runCommand "${runtime.name}-without-emudhra-roots"
          {
            inherit (runtime) meta;
            passthru = (runtime.passthru or { }) // {
              home = "${result}${homeSuffix}";
              jre = result;
            };
          }
          ''
            # Copy rather than rebuild the JDK. Dereference links so neither the
            # launcher nor Darwin's bundle can resolve back to the original store.
            mkdir -p "$out"
            cp -RL ${runtime}/. "$out/"
            chmod -R u+w "$out"
            # OpenJDK's top-level bin is a link into lib/openjdk. Keep launchers
            # beside their libraries rather than running a dereferenced duplicate.
            if [ -n '${homeSuffix}' ]; then
              rm -rf "$out/bin"
              ln -s '.${homeSuffix}/bin' "$out/bin"
            fi
            if [ -f "$out/nix-support/setup-hook" ]; then
              substituteInPlace "$out/nix-support/setup-hook" \
                --replace-fail "${runtime}" "$out"
            fi
            # Linux binary distributions use shell wrappers with absolute paths
            # to their hidden launchers. Copying alone leaves those paths intact.
            while IFS= read -r -d "" launcher; do
              if grep -Iq . "$launcher" && grep -Fq '${runtime}/' "$launcher"; then
                substituteInPlace "$launcher" --replace-fail '${runtime}/' "$out/"
              fi
            done < <(find "$out" -type f -path '*/bin/*' -print0)

            # Cover the OpenJDK lib/openjdk layout, flat JREs, and Darwin bundles.
            while IFS= read -r -d "" cacerts; do
              ${pkgs.jdk21}/bin/java -cp ${tools} RemoveCerts \
                "$cacerts" ${pkgs.lib.escapeShellArgs roots}
            done < <(find "$out" -type f -name cacerts -print0)

            # Fail even if an accidentally unrewritten launcher happens to use
            # an upstream trust store that already passes the certificate check.
            "$out/bin/java" -XshowSettings:properties -version 2> java-properties
            javaHome=$(awk '$1 == "java.home" { print $3 }' java-properties)
            case "$javaHome" in
              "$out"|"$out"/*) ;;
              *) echo "Java escaped the sanitized runtime: $javaHome" >&2; exit 1 ;;
            esac
            "$out/bin/java" -cp ${tools} CheckCerts
          '';
    in
    result;
in
{
  jdk21 = secure pkgs.jdk21;
  jre17 = secure pkgs.temurin-jre-bin-17;
}
