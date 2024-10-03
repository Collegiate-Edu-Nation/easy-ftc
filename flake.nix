{
  description = "easy-ftc Development Environment via Nix Flake";

  inputs = {
    nixpkgs = {
      url = "github:nixos/nixpkgs/nixos-unstable";
    };
    android-nixpkgs = {
      url = "github:tadfisher/android-nixpkgs";
      inputs.nixpkgs.follows = "nixpkgs";
    };
  };

  outputs = { self, nixpkgs, android-nixpkgs }: 
  let
    supportedSystems = [ "x86_64-linux" "aarch64-darwin" ];
    forEachSupportedSystem = function: nixpkgs.lib.genAttrs supportedSystems (system: function {
      pkgs = nixpkgs.legacyPackages.${system};
      android-sdk = android-nixpkgs.sdk.${system} (sdkPkgs: with sdkPkgs; [
        build-tools-30-0-3
        cmdline-tools-12-0
        platforms-android-29
        platform-tools
      ]);
    });
  in {
    devShells = forEachSupportedSystem ({ pkgs, android-sdk }: {
      default = pkgs.mkShell {
        packages = [
          android-sdk
        ] ++ (with pkgs; [
          bashInteractive 
          jdk17
          aapt
        ]);

        # override aapt2 binary w/ nixpkgs'
        GRADLE_OPTS = "-Dorg.gradle.project.android.aapt2FromMavenOverride=${pkgs.aapt}/bin/aapt2";

        shellHook = ''
          echo -e "\easy-ftc Development Environment via Nix Flake\n"
          echo -e "Build:    ./gradlew assemble\n"
          echo -e "Clean:    ./gradlew clean\n"
          echo -e "Test:     ./gradlew test\n"
          echo -e "Coverage: ./gradlew coverage\n"
          echo -e "Docs:     ./gradlew docs"
          java --version
          echo -e "\n"
        '';
      };
    });
  };
}