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
    gradle2nix-input = {
      url = "github:tadfisher/gradle2nix/v2";
      inputs.nixpkgs.follows = "nixpkgs";
    };
  };

  outputs =
    {
      nixpkgs,
      android-nixpkgs,
      gradle2nix-input,
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
          function {
            pkgs = nixpkgs.legacyPackages.${system};
            android-sdk = android-nixpkgs.sdk.${system} (
              sdkPkgs: with sdkPkgs; [
                build-tools-34-0-0
                cmdline-tools-13-0
                platforms-android-30
                platform-tools
              ]
            );
            gradle2nix = gradle2nix-input.builders.${system};
          }
        );
    in
    {
      packages = forEachSupportedSystem (
        { gradle2nix, ... }:
        {
          default = gradle2nix.buildGradlePackage {
            pname = "easy-ftc";
            version = "1.0";

            src = ./.;
            lockFile = ./gradle.lock;

            configurePhase = ''
              mkdir .android
            '';
            gradleBuildFlags = [ ":easy-ftc:assemble" ];
          };
        }
      );
      devShells = forEachSupportedSystem (
        { pkgs, android-sdk, ... }:
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
