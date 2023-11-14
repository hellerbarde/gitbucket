package gitbucket.build;

import io.dagger.client.Client;
import io.dagger.client.Container;
import io.dagger.client.Dagger;
import java.util.List;


public class BuildScala {
    public static void main(String... args) throws Exception {
        try (Client client = Dagger.connect()) {

            Container base = client
                .container()
                .from("maven:3")
                .withDirectory("/appsource", client.host().directory("/home/pstark/git/puzzle/cicd/Dayon"))
                .withWorkdir("/appsource");

            Boolean exportResult = base
                .withExec(List.of("mvn", "package"))
                .file("/appsource/target/dayon.jar")
                .export("/tmp/dayon.jar");

//      base.withExec(List.of("mvn", "test"));
            System.exit(exportResult ? 0 : 1);
        }
    }
}
