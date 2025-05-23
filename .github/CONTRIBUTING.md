<!-- omit in toc -->

# Contributing to easy-ftc

First off, thanks for taking the time to contribute!

All types of contributions are encouraged and valued. See the [Table of Contents](#table-of-contents) for different ways to help and details about how this project handles them. Please make sure to read the relevant section before making your contribution. It will make it a lot easier for us maintainers and smooth out the experience for all involved. The community looks forward to your contributions.

> And if you like the project, but just don't have time to contribute, that's fine. There are other easy ways to support the project and show your appreciation, which we would also be very happy about:
>
> - Star the project
> - Tweet about it
> - Refer this project in your project's readme
> - Mention the project at local meetups and tell your friends/colleagues

<!-- omit in toc -->

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [I Have a Question](#i-have-a-question)
- [I Want To Contribute](#i-want-to-contribute)
  - [Reporting Bugs](#reporting-bugs)
  - [Suggesting Enhancements](#suggesting-enhancements)
  - [Your First Code Contribution](#your-first-code-contribution)
  - [Dev Setup](#dev-setup)
  - [ToDo](#todo)
- [Styleguides](#styleguides)
  - [Source Files](#source-files)
  - [Commit Messages](#commit-messages)
  - [Branch Names](#branch-names)
- [Join The Project Team](#join-the-project-team)

## Code of Conduct

This project and everyone participating in it is governed by the
[easy-ftc Code of Conduct](https://github.com/Collegiate-Edu-Nation/easy-ftc/blob/main/.github/CODE_OF_CONDUCT.md).
By participating, you are expected to uphold this code. Please report unacceptable behavior
to [camdenboren](https://github.com/camdenboren/).

## I Have a Question

> If you want to ask a question, we assume that you have read the available [Documentation](https://collegiate-edu-nation.github.io/easy-ftc).

Before you ask a question, it is best to search for existing [Issues](https://github.com/Collegiate-Edu-Nation/easy-ftc/issues) that might help you. In case you have found a suitable issue and still need clarification, you can write your question in this issue. It is also advisable to search the internet for answers first.

If you then still feel the need to ask a question and need clarification, we recommend the following:

- Open an [Issue](https://github.com/Collegiate-Edu-Nation/easy-ftc/issues/new).
- Provide as much context as you can about what you're running into.
- Provide project and platform versions (nodejs, npm, etc), depending on what seems relevant.

We will then take care of the issue as soon as possible.

<!--
You might want to create a separate issue tag for questions and include it in this description. People should then tag their issues accordingly.

Depending on how large the project is, you may want to outsource the questioning, e.g. to Stack Overflow or Gitter. You may add additional contact and information possibilities:
- IRC
- Slack
- Gitter
- Stack Overflow tag
- Blog
- FAQ
- Roadmap
- E-Mail List
- Forum
-->

## I Want To Contribute

> ### Legal Notice <!-- omit in toc -->
>
> When contributing to this project, you must agree that you have authored 100% of the content, that you have the necessary rights to the content and that the content you contribute may be provided under the project licence.

### Reporting Bugs

<!-- omit in toc -->

#### Before Submitting a Bug Report

A good bug report shouldn't leave others needing to chase you up for more information. Therefore, we ask you to investigate carefully, collect information and describe the issue in detail in your report. Please complete the following steps in advance to help us fix any potential bug as fast as possible.

- Make sure that you are using the latest version.
- Determine if your bug is really a bug and not an error on your side e.g. using incompatible environment components/versions (Make sure that you have read the [documentation](https://collegiate-edu-nation.github.io/easy-ftc). If you are looking for support, you might want to check [this section](#i-have-a-question)).
- To see if other users have experienced (and potentially already solved) the same issue you are having, check if there is not already a bug report existing for your bug or error in the [bug tracker](https://github.com/Collegiate-Edu-Nation/easy-ftc/issues?q=label%3Abug).
- Also make sure to search the internet (including Stack Overflow) to see if users outside of the GitHub community have discussed the issue.
- Collect information about the bug:
  - Stack trace (Traceback)
  - OS, Platform and Version (Windows, Linux, macOS, x86, ARM)
  - Version of the interpreter, compiler, SDK, runtime environment, package manager, depending on what seems relevant.
  - Possibly your input and the output
  - Can you reliably reproduce the issue? And can you also reproduce it with older versions?

<!-- omit in toc -->

#### How Do I Submit a Good Bug Report?

> You must never report security related issues, vulnerabilities or bugs including sensitive information to the issue tracker, or elsewhere in public. Instead sensitive bugs must be sent to [camdenboren](https://github.com/camdenboren/).

<!-- You may add a PGP key to allow the messages to be sent encrypted as well. -->

We use GitHub issues to track bugs and errors. If you run into an issue with the project:

- Open an [Issue](https://github.com/Collegiate-Edu-Nation/easy-ftc/issues/new). (Since we can't be sure at this point whether it is a bug or not, we ask you not to talk about a bug yet and not to label the issue.)
- Explain the behavior you would expect and the actual behavior.
- Please provide as much context as possible and describe the _reproduction steps_ that someone else can follow to recreate the issue on their own. This usually includes your code. For good bug reports you should isolate the problem and create a reduced test case.
- Provide the information you collected in the previous section.

Once it's filed:

- The project team will label the issue accordingly.
- A team member will try to reproduce the issue with your provided steps. If there are no reproduction steps or no obvious way to reproduce the issue, the team will ask you for those steps and mark the issue as `needs-repro`. Bugs with the `needs-repro` tag will not be addressed until they are reproduced.
- If the team is able to reproduce the issue, it will be marked `needs-fix`, as well as possibly other tags (such as `critical`), and the issue will be left to be [implemented by someone](#your-first-code-contribution).

<!-- You might want to create an issue template for bugs and errors that can be used as a guide and that defines the structure of the information to be included. If you do so, reference it here in the description. -->

### Suggesting Enhancements

This section guides you through submitting an enhancement suggestion for easy-ftc, **including completely new features and minor improvements to existing functionality**. Following these guidelines will help maintainers and the community to understand your suggestion and find related suggestions.

<!-- omit in toc -->

#### Before Submitting an Enhancement

- Make sure that you are using the latest version.
- Read the [documentation](https://collegiate-edu-nation.github.io/easy-ftc) carefully and find out if the functionality is already covered, maybe by an individual configuration.
- Perform a [search](https://github.com/Collegiate-Edu-Nation/easy-ftc/issues) to see if the enhancement has already been suggested. If it has, add a comment to the existing issue instead of opening a new one.
- Find out whether your idea fits with the scope and aims of the project. It's up to you to make a strong case to convince the project's developers of the merits of this feature. Keep in mind that we want features that will be useful to the majority of our users and not just a small subset. If you're just targeting a minority of users, consider writing an add-on/plugin library.

<!-- omit in toc -->

#### How Do I Submit a Good Enhancement Suggestion?

Enhancement suggestions are tracked as [GitHub issues](https://github.com/Collegiate-Edu-Nation/easy-ftc/issues).

- Use a **clear and descriptive title** for the issue to identify the suggestion.
- Provide a **step-by-step description of the suggested enhancement** in as many details as possible.
- **Describe the current behavior** and **explain which behavior you expected to see instead** and why. At this point you can also tell which alternatives do not work for you.
- You may want to **include screenshots or screen recordings** which help you demonstrate the steps or point out the part which the suggestion is related to. You can use [LICEcap](https://www.cockos.com/licecap/) to record GIFs on macOS and Windows, and the built-in [screen recorder in GNOME](https://help.gnome.org/users/gnome-help/stable/screen-shot-record.html.en) or [SimpleScreenRecorder](https://github.com/MaartenBaert/ssr) on Linux. <!-- this should only be included if the project has a GUI -->
- **Explain why this enhancement would be useful** to most easy-ftc users. You may also want to point out the other projects that solved it better and which could serve as inspiration.

<!-- You might want to create an issue template for enhancement suggestions that can be used as a guide and that defines the structure of the information to be included. If you do so, reference it here in the description. -->

### Your First Code Contribution

Before submitting a PR, ensure it's addressed by the [ToDo](#todo) or a [GitHub issue](https://github.com/Collegiate-Edu-Nation/easy-ftc/issues). Once you're sure the item is addressed in one (or both) of these locations, follow these steps:

1.  [Fork this repository](https://github.com/Collegiate-Edu-Nation/easy-ftc/fork)
2.  Check out the source code with:

    ```shell
    git clone https://github.com/collegiate-edu-nation/easy-ftc.git
    ```

3.  Switch to dev with

    ```shell
    cd easy-ftc
    git switch dev
    ```

4.  Start a new git branch with

    ```shell
    git checkout -b feature/your-feature
    ```

5.  Make desired changes to `easy-ftc/src` (see [Dev Setup](#dev-setup) for setting up the environment)
6.  Add relevant tests to `easy-ftc/src/test`
7.  Make sure your code, commit history, and branch name all follow the [Styleguides](#styleguides), that your code is properly formatted `./gradlew format`, that your branch builds `./gradlew build`, and that the documentation builds `./gradlew docs`
8.  Finally, [create a pull request](https://help.github.com/articles/creating-a-pull-request). We'll then review and merge it

### Dev Setup

<!-- omit in toc -->

#### Overview

Being an Android project, Gradle is the build system. Note the following

- A Gradle wrapper is used for compatibility with established tooling
  - Some tooling versions are also kept in sync w/ the FTC SDK for the same reason
- Custom Gradle tasks have been created for
  - Running coverage reports
  - Generating docs
  - Verifying format
  - Zipping myBlocks

The custom tasks leverage these tools

- Coverage
  - Jacoco Gradle plugin
- Documentation
  - Javadoc Gradle plugin
  - pip packages
    - MkDocs
    - Material for MkDocs
  - PlantUML
- Formatting
  - Spotless Gradle plugin
  - npm packages
    - prettier
    - npm-groovy-lint
  - nixfmt

> Altogether, properly building this project requires installing and configuring multiple package managers, binaries, runtimes, and interpreters. Thus, if you're not on Windows, I **STRONGLY** recommend using Nix instead of manually setting up the environment, as I wouldn't have bothered using multi-language solutions if this project wasn't powered by Nix

With that said, the development environment can be setup by either using Nix with your IDE of choice or by importing this project into Android Studio. Both options require that you make a local clone of this repo

```shell
git clone https://github.com/collegiate-edu-nation/easy-ftc.git
```

<!-- omit in toc -->

#### Nix

Nix is my preferred approach for setting up the development environment. Linux and macOS are supported

- Enter directory

  ```shell
  cd easy-ftc
  ```

- Launch development environment

  ```shell
  nix develop
  ```

- Then open your preferred IDE from this shell

- Optionally, leverage the binary cache by adding [Garnix] to your nix-config

  ```nix
  nix.settings.substituters = [ "https://cache.garnix.io" ];
  nix.settings.trusted-public-keys = [ "cache.garnix.io:CTFPyKSLcx5RMJKfLo5EEPUObbA78b0YQ2DTCJXqr9g=" ];
  ```

<!-- omit in toc -->

#### Non-Nix

The project can also be imported into Android Studio, where Windows is supported as well

To format source files, you must also install

- Node.js v20.18.1 (LTS)
- nixfmt
  - _Optional as long as no nix files are modified_

Then

- Enter the directory

  ```shell
  cd easy-ftc
  ```

- Install npm deps

  ```shell
  npm i
  ```

- Set `JRE17_PATH` (path to JRE 17 binary, will default to `~/.java-caller`)

  - Linux and macOS

    ```shell
    export JRE17_PATH=path/to/jre17
    ```

  - Windows

    ```shell
    setx JRE17_PATH 'path\to\jre17'
    ```

- Run the format task

  ```shell
  ./gradlew format
  ```

  _Append `-x formatNix` if nixfmt has not been installed_

To generate documentation, you must also install

- Python 3.12.7
- PlantUML
  - _Optional as long as no puml files are modified_

Then

- Enter the directory

  ```shell
  cd easy-ftc
  ```

- Create venv (optional)

  ```shell
  mkdir .venv
  python -m venv .venv
  ```

- Activate venv (optional)

  - Linux and macOS

    ```shell
    source .venv/bin/activate
    ```

  - Windows

    ```shell
    .venv/Scripts/activate
    ```

- Install dependencies

  ```shell
  pip install -r requirements.txt
  ```

- Set `PUML_PATH` (path to PlantUML binary)

  - Linux and macOS

    ```shell
    export PUML_PATH=path/to/plantuml
    ```

  - Windows

    ```shell
    setx PUML_PATH 'path\to\plantuml'
    ```

- Run the docs task

  ```shell
  ./gradlew docs
  ```

  _Append `-x uml` if PlantUML has not been installed_

#### Known Quirks

- Running the build using the Gradle extension for VSCode on NixOS fails as the extension doesn't seem to pass environment variables to the Gradle daemon correctly (note the setting of `GRADLE_OPTS` in `flake.nix`). The workaround is to invoke the build task manually via `./gradlew build`

- The coverage task will not work in Android Studio on Windows, but since it's simply an alias of sorts around `createReleaseUnitTestCoverageReport`, just invoke that task directly

  - The task should work in Android Studio on Linux and macOS once `JAVA_HOME` is specified

    - _Note that this is distinct from `JRE17_PATH`, which is only used for formatting Groovy scripts_

- There seems to be a longstanding bug in Android Studio on macOS where environment variables are not propagated properly, breaking several tasks. The workaround is to launch Android Studio from the terminal via `open /Applications/Android\ Studio.app/`

### ToDo

- [ ] Package for both nix and maven
- [x] Support gyro-based turning for drive in cmd
- [ ] Support CRServo
- [ ] Finish removing 'Object_initializes' tests
- [ ] Flesh out Color
- [ ] Add graphics for blocks and onbot usage
- [ ] Add graphic for controls
- [ ] Look into RACE layout for differential (triggers instead of joystick for axial)
- [ ] Support mechanism + sensor integrations
- [x] Investigate sequence abstraction + implementation
- [ ] Add telemetry for status indicators
- [ ] Add support for OpenCV, AprilTag
- [ ] Investigate options for parallel sequences
- [ ] Investigate further consolidation of builders (esp names, count, etc)
- [ ] Investigate instrumentation and/or manual hardware tests

## Styleguides

### Source Files

<!-- omit in toc -->

#### Formatting and Quality

| Ext      | Fmt                       |
| -------- | ------------------------- |
| java     | google-java-format (aosp) |
| gradle   | npm-groovy-lint           |
| nix      | nixfmt                    |
| md, yaml | prettier                  |

_Sonarlint is used to check code quality_

<!-- omit in toc -->

#### Naming

- Simplicity over descriptivity
- `camelCase` for variables and functions
- `PascalCase` for classes
- `SNAKE_CASE` for constants

<!-- omit in toc -->

#### Javadoc

- Only use one-liners for `private` and `protected` functions, classes
- Include the following for `public` functions and classes (as applicable)
  - `@param`
  - `@return`
  - `@throws`
- Include the following for each `Builder` class
  - Basic Usage
  - Defaults (all default field values)
- Override relevant inherited `Builder` methods

### Commit Messages

#### Hard Rules

- Concise
- Appropriate
- Prefix relevant task followed by a colon where applicable (see [Prefixes](#prefixes))

#### Prefixes

Not all commits require a prefix (e.g. adding input validation), but these common tasks do

- Any
  - 'docs:' when adding a documentation topic
  - 'fix:' when making minor fixes
  - 'fmt:' when applying formatting
  - 'merge:' when merging branches
  - 'move:' for trivial refactoring
  - 'rename:' when renaming things
  - 'refactor:' for non-trivial refactoring
  - 'update:' when updating trivial deps (e.g. plugins)
- Java
  - 'javadoc:' when editing javadoc comments
- Markdown
  - 'sectionName:' when editing a specific section

Other prefixes may be acceptable (e.g. 'uml:'), so use your discretion

### Branch Names

#### Existing Branches

- The latest release is 'main'
- The development branch is 'dev'
- The documentation branch is 'gh-pages'

#### Hard Rules

- Consise
- Appropriate
- Prefix relevant task followed by a forward slash (see [Prefixes](#prefixes-1))

#### Prefixes

- 'feature/' - when adding a feature
- 'update/' - when completing project-wide updates w/ fixes
- 'docs/' - when making major doc modifications
- 'test/' - when making major testing modifications

## Join The Project Team

Requests to join the project team may be submitted to the responsible community leaders at [camdenboren](https://github.com/camdenboren/).

<!-- omit in toc -->

## Attribution

This guide is based on the **contributing-gen**. [Make your own](https://github.com/bttger/contributing-gen)!

[Garnix]: https://garnix.io/
