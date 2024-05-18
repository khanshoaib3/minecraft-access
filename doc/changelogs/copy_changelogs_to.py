#!/usr/bin/env python3

# Copies the changelogs from one file to another

import argparse
import os

CHANGELOG_LINE_IN_PR = "## Changelog"

headings = ['### New Features', '### Feature Updates', '### Bug Fixes',
            '### Translation Changes', '### Others', '### Development Chores']


def main():
    source_path, dest_path = get_file_names_from_cli()

    src_file = open(source_path, "r").readlines()
    dest_file = open(dest_path, "r").readlines()

    src_file = [line.rstrip() for line in src_file]  # Remove trailing spaces
    # Extract changelog part from whole content
    src_file = extract_changelog_from(src_file)
    dest_file = [line.rstrip() for line in dest_file]  # Remove trailing spaces

    print("Source file contents:")
    for line in src_file:
        print(f"{line}")

    print("\nDestination file contents:")
    for line in dest_file:
        print(f"{line}")

    flag = True

    for heading in headings:
        if heading not in src_file:
            continue
        print(f"Heading ({heading}) found...")

        change_logs = get_changelogs_for_heading_in_file(heading, src_file)
        if len(change_logs) == 0:
            continue
        flag = False

        if heading in dest_file:
            insert_index = get_next_heading_index_in_file(dest_file.index(heading), dest_file) - 1
            # Insert at the end of this section
            insert_index = get_next_heading_index_in_file(insert_index, dest_file) - 1
            # edge case
            if insert_index == len(dest_file) - 1:
                insert_index = len(dest_file)
        else:
            # Heading's not preset in the destination file
            change_logs = [heading, ''] + change_logs + ['']
            for j in range(headings.index(heading) + 1, len(headings)):
                if headings[j] in dest_file:
                    insert_index = dest_file.index(headings[j])
                    break
            else:
                insert_index = len(dest_file)
            print(f"Heading not found in destination file, the assumed insert index is {insert_index}")

        print(f"Inserting following lines at index {insert_index}:\n\t{change_logs}")
        front_part = dest_file[:insert_index]
        back_part = dest_file[insert_index:]
        dest_file = front_part

        # Keep one empty line between items and headers
        if front_part[-1].startswith("#"):
            dest_file += [""] + change_logs
        else:
            dest_file += change_logs

        if len(back_part) == 0 or back_part[0] == "":
            dest_file += back_part
        else:
            dest_file += [""] + back_part

    if flag:
        print("\n\nNo changes to write!")
    else:
        dest_file_w = open(dest_path, "w")
        dest_file = [line + "\n" for line in dest_file]  # Add trailing line break
        print("\n\nOverwriting destination file with the content:")
        print(*dest_file)
        dest_file_w.writelines(dest_file)


def extract_changelog_from(lines: list[str]) -> list[str]:
    start = 0
    changelog_encountered = False
    end = len(lines)

    for i, line in enumerate(lines):
        if line.startswith(CHANGELOG_LINE_IN_PR):
            start = i + 1
            changelog_encountered = True
            continue
        if changelog_encountered and line.startswith("## "):
            end = i
            break

    return lines[start: end]


def get_changelogs_for_heading_in_file(heading: str, file_contents: list) -> list:
    def replace_list_item_marker(line: str) -> str:
        if line.startswith("* "):
            return line.replace("* ", "- ")
        else:
            return line

    heading_index = file_contents.index(heading)
    next_heading_index = get_next_heading_index_in_file(heading_index, file_contents)

    change_logs = file_contents[heading_index + 1: next_heading_index]
    change_logs = list(filter(lambda x: x.strip() != '', change_logs))  # Remove empty lines
    # Remove markdown comments
    change_logs = list(filter(lambda x: not x.startswith("[//]:"), change_logs))
    # Replace list item markers * to -
    change_logs = list(map(replace_list_item_marker, change_logs))

    return change_logs


def get_next_heading_index_in_file(current_heading_index: int, file_contents: list) -> int:
    start_index = current_heading_index + 1
    for index, line in enumerate(file_contents[start_index:], start=start_index):
        if line in headings:
            return index
    return len(file_contents)


def get_file_names_from_cli():
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "source", help='The file path from which the changelogs are to be extracted.')
    parser.add_argument(
        "destination", help='The file path to which the changelogs are to be written.')

    parsed_args = parser.parse_args()
    if not os.path.exists(parsed_args.source):
        print(f"File `{parsed_args.source}` not found!")
        exit(0)

    if not os.path.exists(parsed_args.destination):
        print(f"File `{parsed_args.destination}` not found!")
        exit(0)

    return [parsed_args.source, parsed_args.destination]


if __name__ == "__main__":
    main()
