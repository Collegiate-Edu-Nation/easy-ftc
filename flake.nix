# SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
# SPDX-License-Identifier: GPL-3.0-or-later

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
    gradle-dot-nix = {
      url = "github:CrazyChaoz/gradle-dot-nix";
      inputs.nixpkgs.follows = "nixpkgs";
    };
  };

  outputs =
    {
      nixpkgs,
      android-nixpkgs,
      gradle-dot-nix,
      ...
    }:
    let
      supportedSystems = [
        "x86_64-linux"
        "aarch64-darwin"
      ];
      forEachSupportedSystem =
        function:
        nixpkgs.lib.genAttrs supportedSystems (
          system:
          function rec {
            pkgs = nixpkgs.legacyPackages.${system};
            android-sdk = android-nixpkgs.sdk.${system} (
              sdkPkgs: with sdkPkgs; [
                build-tools-34-0-0
                cmdline-tools-13-0
                platforms-android-30
                platform-tools
              ]
            );
            gradle-init-script =
              (import gradle-dot-nix {
                inherit pkgs;
                gradle-verification-metadata-file = ./gradle/verification-metadata.xml;
              }).gradle-init;
          }
        );
    in
    {
      packages = forEachSupportedSystem (
        {
          pkgs,
          android-sdk,
          gradle-init-script,
        }:
        {
          default = pkgs.stdenv.mkDerivation {
            name = "easy-ftc";
            version = "1.0";

            JDK_HOME = "${pkgs.jdk21.home}";
            ANDROID_HOME = "${android-sdk}/share/android-sdk";

            src = ./.;
            nativeBuildInputs = [
              android-sdk
              pkgs.gradle
              pkgs.jdk21
            ];
            configurePhase = ''
              mkdir .android
            '';
            buildPhase = ''
              gradle build --info -I ${gradle-init-script} --offline --full-stacktrace -Dorg.gradle.project.android.aapt2FromMavenOverride=$ANDROID_HOME/build-tools/34.0.0/aapt2
            '';
            installPhase = ''
              mkdir -p $out
              cp -r ./easy-ftc/build/outputs/aar/easy-ftc-release.aar $out
            '';
          };
        }
      );
      devShells = forEachSupportedSystem (
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
      );
    };
}
