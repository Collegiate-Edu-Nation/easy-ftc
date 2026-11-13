# SPDX-FileCopyrightText: Collegiate Edu-Nation
# SPDX-License-Identifier: GPL-3.0-or-later

{ pkgs, android-sdk }:

let
  java = import ./java { inherit pkgs; };
in
{
  inherit (java) jdk21 jre17;

  build = [
    android-sdk
  ]
  ++ (
    with pkgs;
    with pkgs.python314Packages;
    [
      java.jdk21
      aapt
      jdt-language-server

      # docs
      plantuml
      mkdocs
      mkdocs-material

      # formatting
      nixfmt
      (callPackage ./npm-groovy-lint { })
      prettier
      java.jre17
    ]
  );
}
