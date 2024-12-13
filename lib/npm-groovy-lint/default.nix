# SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
# SPDX-License-Identifier: GPL-3.0-or-later

{ buildNpmPackage, fetchFromGitHub }:

buildNpmPackage rec {
  pname = "npm-groovy-lint";
  version = "15.0.2";
  src = fetchFromGitHub {
    owner = "nvuillam";
    repo = pname;
    rev = "v${version}";
    hash = "sha256-WLQH+BkPl2Urih2VpcCMSM85RKEwLA/Cn/Cux98AknU=";
  };

  npmDepsHash = "sha256-wdclH75A8IPgjh9eimSt2OGTPZOHddU/z8Jf8lwaDlE=";
}
