# nixpkg Blocker

When running `nix build`, the following error causes it to fail

```shell
Gradle could not start your build.
       > > Could not initialize native services.
       >    > Failed to load native library 'libnative-platform.dylib' for Mac OS X aarch64.
```
