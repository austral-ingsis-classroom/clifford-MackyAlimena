package edu.austral.ingsis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandTests {

    private CommandRunner runner;

    @BeforeEach
    void setUp() {
        runner = new CommandRunner();
    }

    @Test
    void givenMkdirCommand_whenExecuted_thenDirectoryCreated() {
        List<String> commands = List.of("mkdir documents");
        List<String> result = runner.executeCommands(commands);

        assertEquals(List.of("'documents' directory created"), result);
    }

    @Test
    void givenDuplicateMkdirCommand_whenExecuted_thenError() {
        List<String> commands = List.of("mkdir folder", "mkdir folder");
        List<String> result = runner.executeCommands(commands);

        assertEquals(List.of("'folder' directory created", "Directory already exists"), result);
    }

    @Test
    void givenCdAndPwdCommands_whenExecuted_thenCorrectDirectoryShown() {
        List<String> commands = List.of("mkdir myfolder", "cd myfolder", "pwd");
        List<String> result = runner.executeCommands(commands);

        assertEquals(
                List.of(
                        "'myfolder' directory created",
                        "moved to directory 'myfolder'",
                        "/myfolder"
                ),
                result
        );
    }

    @Test
    void givenTouchCommand_whenExecuted_thenFileCreated() {
        List<String> commands = List.of("touch file.txt");
        List<String> result = runner.executeCommands(commands);

        assertEquals(List.of("'file.txt' file created"), result);
    }

    @Test
    void givenRmCommandOnFile_whenExecuted_thenFileRemoved() {
        List<String> commands = List.of("touch file.txt", "rm file.txt");
        List<String> result = runner.executeCommands(commands);

        assertEquals(
                List.of(
                        "'file.txt' file created",
                        "'file.txt' removed"
                ),
                result
        );
    }

    @Test
    void givenRmRecursiveOnFolder_whenExecuted_thenFolderRemoved() {
        List<String> commands = List.of("mkdir myfolder", "rm --recursive myfolder");
        List<String> result = runner.executeCommands(commands);

        assertEquals(
                List.of(
                        "'myfolder' directory created",
                        "'myfolder' removed"
                ),
                result
        );
    }
}
