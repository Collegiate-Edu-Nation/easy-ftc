# SPDX-FileCopyrightText: 2024-2025 Collegiate Edu-Nation
# SPDX-License-Identifier: GPL-3.0-or-later

{
  description = "easy-ftc Development Environment via Nix Flake";

  nixConfig.bash-prompt = ''\n\[\033[1;31m\][devShell:\w]\$\[\033[0m\] '';

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
      nixpkgs,
      android-nixpkgs,
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
            deps = (import ./nix/deps.nix { inherit pkgs android-sdk; });
          }
        );
    in
    {
      devShells = forEachSupportedSystem (import ./nix/shell.nix);
    };
}
