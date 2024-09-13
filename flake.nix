{
  description = "easy-ftc Development Environment via Nix Flake";

  inputs = {
    nixpkgs = {
      url = "github:nixos/nixpkgs/nixos-unstable";
    };
    nixpkgs-old = {
      url = "github:nixos/nixpkgs/6f884c2f43c7bb105816303eb4867da672ec6f39";
    };
    android-nixpkgs = {
      url = "github:tadfisher/android-nixpkgs";
      inputs.nixpkgs.follows = "nixpkgs";
    };
  };

  outputs = { self, nixpkgs, nixpkgs-old, android-nixpkgs }: 
  let
    supportedSystems = [ "x86_64-linux" "aarch64-linux" "aarch64-darwin" ];
    forEachSupportedSystem = function: nixpkgs.lib.genAttrs supportedSystems (system: function {
      pkgs = nixpkgs.legacyPackages.${system};
      pkgs-old = nixpkgs-old.legacyPackages.${system};
      android-sdk = android-nixpkgs.sdk.${system} (sdkPkgs: with sdkPkgs; [
        build-tools-30-0-3
        cmdline-tools-12-0
        platforms-android-29
        platform-tools
      ]);
    });
  in {
    devShells = forEachSupportedSystem ({ pkgs, pkgs-old, android-sdk }: {
      default = pkgs.mkShell {
        packages = [
          android-sdk
        ] ++ (with pkgs; [
          bashInteractive 
          jdk17
          aapt
        ]) ++ (with pkgs-old; [
          gradle
        ]);

        # override aapt2 binary w/ nixpkgs'
        GRADLE_OPTS = "-Dorg.gradle.project.android.aapt2FromMavenOverride=${pkgs.aapt}/bin/aapt2";

        shellHook = ''
          echo -e "\easy-ftc Development Environment via Nix Flake\n"
          echo -e "Build: gradle build\n"
          echo -e "Clean: gradle clean\n"
          java --version
        '';
      };
    });
  };
}