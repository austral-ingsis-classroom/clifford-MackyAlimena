package edu.austral.ingsis.clifford.commands.types;

import edu.austral.ingsis.clifford.commands.result.Result;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.node.FileSystemNode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LsCommand implements Command {
  @Override
  public Result execute(FileSystem fileSystem, String argument) {
    List<FileSystemNode> children = fileSystem.getCurrent().getChildren();

    if (children.isEmpty()) return Result.success(fileSystem, "");

    List<FileSystemNode> resultList = new ArrayList<>(children);

    if ("--ord=asc".equals(argument)) {
      resultList.sort(Comparator.naturalOrder());
    } else if ("--ord=desc".equals(argument)) {
      resultList.sort(Comparator.reverseOrder());
    }

    String result = resultList.stream().map(FileSystemNode::name).collect(Collectors.joining(" "));
    return Result.success(fileSystem, result);
  }
}
