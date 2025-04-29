package edu.austral.ingsis.clifford.commands.result;

import edu.austral.ingsis.clifford.filesystem.FileSystem;

public class Result {
  private final FileSystem fileSystem;
  private final String message;
  private final ResultType type;

  public Result(FileSystem fileSystem, String message, ResultType type) {
    this.fileSystem = fileSystem;
    this.message = message;
    this.type = type;
  }

  public FileSystem getFileSystem() {
    return fileSystem;
  }

  public String getMessage() {
    return message;
  }

  public ResultType getType() {
    return type;
  }

  public static Result success(FileSystem fs, String msg) {
    return new Result(fs, msg, ResultType.SUCCESS);
  }

  public static Result error(FileSystem fs, String msg) {
    return new Result(fs, msg, ResultType.ERROR);
  }

  public static Result info(FileSystem fs, String msg) {
    return new Result(fs, msg, ResultType.INFO);
  }
}
