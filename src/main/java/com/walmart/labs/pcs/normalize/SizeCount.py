import sys
import os
from datetime import datetime

if len(sys.argv) != 4:
    sys.stderr.write("Invalid command\n")
    sys.exit(1)

data_filename = sys.argv[1]
dept_id = sys.argv[2]
attr_name = sys.argv[3]

print "department id is: %s" % dept_id
print "attribute name is: %s" % attr_name

report_filename = 'data/report.txt'

directory = os.path.dirname(report_filename)
if not os.path.exists(directory):
    os.makedirs(directory)

item_counts = dict()
total_num_items = 0
with open(data_filename) as data_fh:
    for line in data_fh:
        if line.find(dept_id) != -1 and line.find(attr_name) != -1:
            total_num_items += 1
            fields = line.strip().split("\t")
            for field in fields:
                if(field.startswith( attr_name )):
                    attr_value = field[len(attr_name):]
                    if attr_value in item_counts:
                        item_counts[attr_value] += 1
                    else:
                        item_counts[attr_value] = 1
with open(report_filename, 'w') as report_fh:
    for attr_value in item_counts:
        report_fh.write(attr_name + attr_value + ':' + str(item_counts[attr_value]) + '\n')

print "Total # of items: %d" % total_num_items
