{ pkgs, android-sdk }:

{
  default = pkgs.mkShell {
    packages =
      [
        android-sdk
      ]
      ++ (
        with pkgs;
        with pkgs.python312Packages;
        with pkgs.nodePackages;
        [
          bashInteractive
          jdk21
          aapt

          # docs
          plantuml
          mkdocs
          mkdocs-material

          # formatting
          nixfmt-rfc-style
          (callPackage ./lib/npm-groovy-lint { })
          prettier
          temurin-jre-bin-17
        ]
      );

    # set env vars for binary paths
    GRADLE_OPTS = "-Dorg.gradle.project.android.aapt2FromMavenOverride=${pkgs.aapt}/bin/aapt2";
    JRE17_PATH = "${pkgs.temurin-jre-bin-17}/bin/java";
    PUML_PATH = "${pkgs.plantuml}/bin/plantuml";

    shellHook = ''
      echo -e "\neasy-ftc Development Environment via Nix Flake\n"

      echo -e "┌───────────────────────────────┐"
      echo -e "│      Custom Gradle Tasks      │"
      echo -e "├──────────┬────────────────────┤"
      echo -e "│ Coverage │ ./gradlew coverage │"
      echo -e "│ Docs     │ ./gradlew docs     │"
      echo -e "│ Format   │ ./gradlew format   │"
      echo -e "│ myBlocks │ ./gradlew myBlocks │"
      echo -e "└──────────┴────────────────────┘\n"
    '';
  };
}
