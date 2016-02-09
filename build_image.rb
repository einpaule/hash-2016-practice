#!/usr/bin/ruby

ON = '*'
OFF = '.'

####
## Params management
####
if ARGV.size != 3
  puts "usage:"
  puts "\truby #{$PROGRAM_NAME} 5 7 'PAINT_SQUARE 2 3 1;ERASE_CELL 2 3;PAINT_SQUARE 0 4 0;PAINT_SQUARE 4 2 0'"
  puts "\tor'"
  puts "\truby #{$PROGRAM_NAME} 5 7 ./commands.txt"
  exit
end

ROWS = ARGV[0].to_i
COLUMNS = ARGV[1].to_i
if File.exist?(ARGV[2])
  COMMANDS = File.read(ARGV[2]).split("\n")[1..-1].map(&:split)
else
  COMMANDS = ARGV[2].split(';').map(&:split)
end

####
## Library
####
def image_from_commands(rows: 1, columns: 1, commands: [])
  image = Array.new(rows) { Array.new(columns) { OFF } }

  commands.each do |command|
    name = command[0]
    coordinates = command[1..-1].map(&:to_i)
    case name
    when 'PAINT_LINE'
      image = paint_line(image: image, r1: coordinates[0], c1: coordinates[1], r2: coordinates[2], c2: coordinates[3])
    when 'PAINT_SQUARE'
      image = paint_square(image: image, r: coordinates[0], c: coordinates[1], s: coordinates[2])
    when 'ERASE_CELL'
      image = erase_cell(image: image, r: coordinates[0], c: coordinates[1])
    else
      fail "Unknown command '#{name}'"
    end
  end
  image
end

def paint_line(image:, r1:, c1:, r2:, c2:)
  fail "OUT OF BOUND coordinates for PAINT_LINE #{r1} #{c1} #{r2} #{c2}'" if invalid_coordinates?(rows: [r1, r2], columns: [c1, c2])
  if r1 == r2 # horizontal line
    (c1..c2).each { |y_coord| image[r1][y_coord] = ON }
  elsif c1 == c2 # vertical line
    (r1..r2).each { |x_coord| image[x_coord][c1] = ON }
  else
    fail "INVALID coordinates for PAINT_LINE #{r1} #{c1} #{r2} #{c2}' (not a valid line)"
  end
  image
end

def paint_square(image:, r:, c:, s:)
  r1 = r - s
  r2 = r + s
  c1 = c - s
  c2 = c + s
  fail "OUT OF BOUND coordinates for PAINT_SQUARE #{r} #{c} #{s}'" if invalid_coordinates?(rows: [r1, r2], columns: [c1, c2])
  (r1..r2).each do |x|
    (c1..c2).each do |y|
      image[x][y] = ON
    end
  end
  image
end

def erase_cell(image:, r:, c:)
  fail "OUT OF BOUND coordinates for ERASE_CELL #{r} #{c}'" if invalid_coordinates?(rows: [r], columns: [c])
  image[r][c] = OFF
  image
end

def invalid_coordinates?(rows: [], columns: [])
  rows.any? { |r| r < 0 || r >= ROWS } || columns.any? { |c| c < 0 || c >= COLUMNS }
end

def display_image(image: [])
  puts image.map(&:join).join("\n")
end

####
## MAIN
####
image = image_from_commands(rows: ROWS, columns: COLUMNS, commands: COMMANDS)
display_image(image: image)
