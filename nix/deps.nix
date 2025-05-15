# SPDX-FileCopyrightText: 2025 Collegiate Edu-Nation
# SPDX-License-Identifier: GPL-3.0-or-later

{ pkgs, android-sdk }:

{
  build =
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
        (callPackage ./npm-groovy-lint { })
        prettier
        temurin-jre-bin-17
      ]
    );
}
