{ pkgs, android-sdk }:

let
  gradle = pkgs.gradle_8;
  self = pkgs.stdenv.mkDerivation (finalAttrs: {
    pname = "easy-ftc";
    version = "1.0";
    src = ./.;

    nativeBuildInputs =
      [
        android-sdk
      ]
      ++ (with pkgs; [
        gradle
        jdk21
        aapt
      ]);

    env.GRADLE_OPTS = "-Dorg.gradle.project.android.aapt2FromMavenOverride=${pkgs.aapt}/bin/aapt2";

    __darwinAllowLocalNetworking = true;
    mitmCache = gradle.fetchDeps {
      pkg = self;
      data = ./deps.json;
    };

    doCheck = true;

    meta.sourceProvenance = with pkgs.lib.sourceTypes; [
      fromSource
      binaryBytecode # mitm cache
    ];
  });
in
{
  default = self;
  updateDeps = pkgs.writeShellApplication {
    name = "update-deps.sh";
    text = ''
      exec ${self.mitmCache.updateScript}
    '';
  };
}
