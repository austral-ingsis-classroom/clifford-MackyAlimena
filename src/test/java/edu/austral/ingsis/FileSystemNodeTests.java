package edu.austral.ingsis;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import edu.austral.ingsis.clifford.commands.CommandParser;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import org.junit.jupiter.api.Test;

public class FileSystemNodeTests {

  private final CommandRunner runner = new CommandRunner();

    private void executeTest(List<Map.Entry<String, String>> commands) {
        CommandParser parser = new CommandParser();
        for (Map.Entry<String, String> entry : commands) {
            String command = entry.getKey();
            String expected = entry.getValue();
            String actual = parser.parse(command);

            // LOG: estado actual del FileSystem
            System.out.println("--- After command: " + command + " ---");
            System.out.println("Expected: " + expected);
            System.out.println("Actual  : " + actual);
            System.out.println("Current Dir: " + parser.getFileSystem().getCurrent().name());
            System.out.println("Root Children: ");
            parser.getFileSystem().getRoot().getChildren()
                    .forEach(child -> System.out.println("  - " + child.name()));
            System.out.println("----------------------------");

            assertEquals(expected, actual);
        }
    }



    @Test
  public void test1() {
    executeTest(
        List.of(
            entry("ls", ""),
            entry("mkdir horace", "'horace' directory created"),
            entry("ls", "horace"),
            entry("mkdir emily", "'emily' directory created"),
            entry("ls", "horace emily"),
            entry("ls --ord=asc", "emily horace")));
  }

  @Test
  void test2() {
    executeTest(
        List.of(
            entry("mkdir horace", "'horace' directory created"),
            entry("mkdir emily", "'emily' directory created"),
            entry("mkdir jetta", "'jetta' directory created"),
            entry("ls", "horace emily jetta"),
            entry("cd emily", "moved to directory 'emily'"),
            entry("pwd", "/emily"),
            entry("touch elizabeth.txt", "'elizabeth.txt' file created"),
            entry("mkdir t-bone", "'t-bone' directory created"),
            entry("ls", "elizabeth.txt t-bone")));
  }

  @Test
  void test3() {
    executeTest(
        List.of(
            entry("mkdir horace", "'horace' directory created"),
            entry("mkdir emily", "'emily' directory created"),
            entry("mkdir jetta", "'jetta' directory created"),
            entry("cd emily", "moved to directory 'emily'"),
            entry("ls", "")));
  }

  @Test
  void test4() {
      System.out.println("--- Test Start ---");
    executeTest(
        List.of(
            entry("mkdir horace", "'horace' directory created"),
            entry("mkdir emily", "'emily' directory created"),
            entry("cd horace", "moved to directory 'horace'"),
            entry("mkdir jetta", "'jetta' directory created"),
            entry("cd ..", "moved to directory '/'"),
            entry("cd horace/jetta", "moved to directory 'jetta'"),
            entry("pwd", "/horace/jetta"),
            entry("cd /", "moved to directory '/'")));
      System.out.println("--- Test Finnish ---");
  }

  @Test
  void test5() {
    executeTest(
        List.of(
            entry("mkdir emily", "'emily' directory created"),
            entry("cd horace", "'horace' directory does not exist")));
  }

  @Test
  void test6() {
    executeTest(List.of(entry("cd ..", "moved to directory '/'")));
  }

  @Test
  void test7() {
    executeTest(
        List.of(
            entry("mkdir horace", "'horace' directory created"),
            entry("cd horace", "moved to directory 'horace'"),
            entry("touch emily.txt", "'emily.txt' file created"),
            entry("touch jetta.txt", "'jetta.txt' file created"),
            entry("ls", "emily.txt jetta.txt"),
            entry("rm emily.txt", "'emily.txt' removed"),
            entry("ls", "jetta.txt")));
  }

  @Test
  void test8() {
    executeTest(
        List.of(
            entry("mkdir emily", "'emily' directory created"),
            entry("cd emily", "moved to directory 'emily'"),
            entry("mkdir emily", "'emily' directory created"),
            entry("touch emily.txt", "'emily.txt' file created"),
            entry("touch jetta.txt", "'jetta.txt' file created"),
            entry("ls", "emily emily.txt jetta.txt"),
            entry("rm --recursive emily", "'emily' removed"),
            entry("ls", "emily.txt jetta.txt"),
            entry("ls --ord=desc", "jetta.txt emily.txt")));
  }
}
