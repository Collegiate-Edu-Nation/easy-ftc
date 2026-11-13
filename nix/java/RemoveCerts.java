// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;

public class RemoveCerts {
    public static void main(String[] args) throws Exception {
        Path path = Path.of(args[0]);
        char[] password = "changeit".toCharArray();
        KeyStore store = KeyStore.getInstance(path.toFile(), password);
        var roots = Arrays.asList(args).subList(1, args.length);

        // Match subjects, not vendor-specific aliases. Missing roots are OK.
        for (String alias : Collections.list(store.aliases())) {
            var cert = (X509Certificate) store.getCertificate(alias);
            String subject = cert.getSubjectX500Principal().getName();
            if (roots.stream().anyMatch(root -> subject.contains("CN=" + root))) {
                System.out.println("Removing " + subject);
                store.deleteEntry(alias);
            }
        }

        try (var output = Files.newOutputStream(path)) {
            store.store(output, password);
        }
    }
}
