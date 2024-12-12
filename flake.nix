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
  };

  outputs =
    {
      self,
      nixpkgs,
      android-nixpkgs,
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
          }
        );
    in
    {
      devShells = forEachSupportedSystem (
        { pkgs, android-sdk }:
        {
          default = pkgs.mkShell {
            packages =
              [
                android-sdk
              ]
              ++ (with pkgs; [
                bashInteractive
                jdk21

                # docs
                plantuml
                python312Packages.mkdocs
                python312Packages.mkdocs-material

                # formatting
                nixfmt-rfc-style
                nodejs
                jre17_minimal
              ]);

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

              if ! test -d node_modules; then
                echo -e "Adding npm-groovy-lint and prettier..."
                npm install
                echo -e "Done.\n"
              fi
            '';
          };
        }
      );
    };
}
