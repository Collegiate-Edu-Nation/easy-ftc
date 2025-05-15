# SPDX-FileCopyrightText: 2025 Collegiate Edu-Nation
# SPDX-License-Identifier: GPL-3.0-or-later

{ pkgs, deps, ... }:

{
  default = pkgs.mkShell {
    packages = deps.build;

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
