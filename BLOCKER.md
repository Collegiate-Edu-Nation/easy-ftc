# nixpkg Blocker

When executing nixDownloadDeps task via `nix run .#updateDeps`, the following error causes it to fail

```shell
Execution failed for task ':easy-ftc:nixDownloadDeps'.
> Could not resolve all files for configuration ':easy-ftc:releaseUnitTestCompileClasspath'.
   > The consumer was configured to find a library for use during compile-time, preferably optimized for Android, as well as attribute 'com.android.build.api.attributes.AgpVersionAttr' with value '8.7.0', attribute 'com.android.build.api.attributes.BuildTypeAttr' with value 'release', attribute 'org.jetbrains.kotlin.platform.type' with value 'androidJvm'. However we cannot choose between the following variants of project :easy-ftc:
```
