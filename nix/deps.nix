# SPDX-FileCopyrightText: Collegiate Edu-Nation
# SPDX-License-Identifier: GPL-3.0-or-later

{ pkgs, android-sdk }:

{
  build = [
    android-sdk
  ]
  ++ (
    with pkgs;
    with pkgs.python314Packages;
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
      nixfmt
      (callPackage ./npm-groovy-lint { })
      prettier
      temurin-jre-bin-17
    ]
  );
}
