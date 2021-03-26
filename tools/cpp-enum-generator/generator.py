#!/bin/python3

import re
import os
import sys
import argparse

class Generator:
    def __init__(self, filename):
        self.regular_source_file_ending = "hpp"
        self.enum_class = filename.split("/")[-1].replace(".txt", "")
        self.first_element = ""
        self.last_element = ""
        self.size = 0
        self.other_elements = list()
        self.constants = dict()
        self.__get_elements__(filename)
        print("Enum class-name is:", self.enum_class)
        print("Elements are:")
        print(self.first_element)
        for elem in self.other_elements:
            print(elem)
        print(self.last_element)
        
    def __get_elements__(self, filename):
        elements = list()
        with open(filename, "r") as f:
            elements = f.read().splitlines()
        self.size = len(elements)
        self.first_element = elements[0]
        elements = elements[1:]
        self.last_element = elements.pop()
        self.other_elements = elements
    
    def __set_constants__(self, line_templates):
        for line in line_templates:
            if re.match(r"\$CONSTANT\$", line):
                match = re.split(r"\W+", line)
                value_pos = -1
                if match[-1] == "":
                    value_pos = -2
                value = match[value_pos]
                constant = "$" + match[value_pos - 1] + "$"
                self.constants[constant] = value
        print(self.constants)
                
    
    def __write_file__(self, line_templates, ending, output_path):
        if output_path == "":
            output_path = "."
        if not os.path.exists(output_path):
            raise Exception("Output path does not exist")
        with open(os.path.abspath(output_path) + "/" + self.enum_class + "." + ending, "w") as out_file:
            for line_template in line_templates:
                line = line_template + "\n"
                line = line.replace("$ENUM_CLASS$", self.enum_class)
                line = line.replace("$ENUM_CLASS_CAP$", self.enum_class.upper())
                line = line.replace("$SIZE$", str(self.size))
                for constant in self.constants.keys():
                    line = line.replace(constant, self.constants[constant])
                if "$FIRST_ENUM$" in line:
                    out_file.write(line.replace("$FIRST_ENUM$", self.first_element))
                elif "$LAST_ENUM$" in line:
                    out_file.write(line.replace("$LAST_ENUM$", self.last_element))
                elif "$MIDDLE_ENUM$" in line:
                    for enum_elem in self.other_elements:
                        out_file.write(line.replace("$MIDDLE_ENUM$", enum_elem))
                else:
                    out_file.write(line)
    
    def get_files(self, template_path, output_path):
        line_templates = list()
        with open(template_path, "r") as template:
            line_templates = template.read().splitlines()
        self.__set_constants__(line_templates)
        file_template = list()
        file_ending = ""
        for line in line_templates:
            # filter out comments and jump over lines with constant definitions
            if re.match(r";", line):
                continue
            if re.match(r"\$CONSTANT\$", line):
                continue
            
            # Find file endings
            if "$FILE_ENDING$" in line:
                match = re.split(r"\W+", line)
                file_ending = match[-1]
                if file_ending == "":
                    file_ending = match[-2]
                self.__write_file__(file_template, file_ending, output_path)
                file_template = list()
                continue
            
            file_template.append(line)
        
        # if no file ending is set, use the 
        if file_ending == "":
            self.__write_file__(file_template, self.regular_source_file_ending, output_path)


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("enum_list_file", help="Path to a file containing a list of enums to be generated.")
    parser.add_argument("-t", "--template_path", help="If template is not located in bin folder of script, please specify here.")
    parser.add_argument("-o", "--output_path", help="Specify a path to a directory to place the generated file(s).")
    
    args = parser.parse_args()
    generator = Generator(args.enum_list_file)
    template_path = "template.txt"
    if args.template_path:
        template_path = args.template_path
    output_path = ""
    if args.output_path:
        output_path = args.output_path    
    generator.get_files(template_path, output_path)
