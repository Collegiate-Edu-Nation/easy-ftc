# SPDX-FileCopyrightText: Collegiate Edu-Nation
# SPDX-License-Identifier: GPL-3.0-or-later

{ buildNpmPackage, fetchFromGitHub }:

buildNpmPackage rec {
  pname = "npm-groovy-lint";
  version = "16.1.1";
  src = fetchFromGitHub {
    owner = "nvuillam";
    repo = pname;
    rev = "v${version}";
    hash = "sha256-tW2HAkN2nV0lOY3H98OvoY5EDSG3z2MDL0270wOI8m4=";
  };

  npmDepsHash = "sha256-kSUtKfmXSIr75sbLPQ7wGZW/PBS1eHjvcZq4aQJs22E=";
}
