# Java trust-store mitigation

Both devShell runtimes use copies of the pinned JDK/JRE with the four roots
identified by Oracle's `CheckCerts.java` removed from their bundled trust stores:

- emSign ECC Root CA - G3
- AAA Certificate Services
- emSign Root CA - G1
- emSign Root CA - G2

This deliberately distrusts all chains anchored at these roots, including
otherwise legitimate certificates. Other roots are retained. Explicit custom
trust stores are not modified or protected by this mitigation.

`default.nix` also sanitizes macOS bundle trust stores and runs Oracle's checker
as a build-time assertion for each runtime. `CheckCerts.java` is the
Oracle-provided source from https://cr.openjdk.org/~mr/CheckCerts.java.
