require 'matrix'

class Cell
  attr_accessor :r, :c

  def initialize(r, c)
    @r = r
    @c = c
  end
end

class Line
  attr_accessor :cell1, :cell2

  def initialize(cell1 = nil, cell2 = nil)
    @cell1 = cell1
    @cell2 = cell2
  end

  def length
    return -1 if @cell1.nil? || @cell2.nil?
    (@cell2.r - @cell1.r).abs + (@cell2.c - @cell1.c).abs
  end
end

class Square
  attr_accessor :cell, :size

  def initialize(cell, size)
    @cell = cell
    @size = size
  end
end

class Command
  attr_accessor :name, :params

  def initialize(name:, params:)
    @name = name
    @params = params
  end

  def output
    "#{@name} #{@params.join(' ')}"
  end
end

class GImage
  attr_accessor :matrix, :commands

  def initialize(matrix:, commands: [])
    @matrix = matrix.map { |e| (e == '#') ? 1 : 0 }
    @temp_matrix = @matrix.clone
    @commands = commands
  end

  def print
    puts @matrix.map { |e| (e == 1) ? '#' : '.' }.to_a.map(&:join).join("\n")
  end

  def solve
    @temp_matrix = @matrix.clone
    while line = find_longest_line
      command = Command.new(name: 'PAINT_LINE', params: [line.cell1.r, line.cell1.c, line.cell2.r, line.cell2.c])
      commands.push(command)
      update_matrix_with(command)
    end
    commands
  end

  private

  def find_longest_line
    longest_line = Line.new

    # horizontal lines
    current_line = Line.new
    pending_line = false
    (0..@temp_matrix.row_count - 1).each do |r|
      row = @temp_matrix.row(r)
      row.each_with_index do |e, i|
        if pending_line
          if e == 1
            current_line.cell2 = Cell.new(r, i)
          else
            longest_line = current_line if current_line.length > longest_line.length
            pending_line = false
            current_line = Line.new
          end
        else
          if e == 1
            pending_line = true
            current_line.cell1 = Cell.new(r, i)
            current_line.cell2 = Cell.new(r, i)
          end
        end
      end
      longest_line = current_line if current_line.length > longest_line.length
      pending_line = false
      current_line = Line.new
    end

    # vertical lines
    pending_line = false
    current_line = Line.new
    (0..@temp_matrix.column_count - 1).each do |c|
      col = @temp_matrix.column(c)
      col.each_with_index do |e, i|
        if pending_line
          if e == 1
            current_line.cell2 = Cell.new(i, c)
          else
            longest_line = current_line if current_line.length > longest_line.length
            pending_line = false
            current_line = Line.new
          end
        else
          if e == 1
            pending_line = true
            current_line.cell1 = Cell.new(i, c)
            current_line.cell2 = Cell.new(i, c)
          end
        end
      end
      longest_line = current_line if current_line.length > longest_line.length
      pending_line = false
      current_line = Line.new
    end

    (longest_line.length == -1) ? nil : longest_line
  end

  def update_matrix_with(command)
    case command.name
    when 'PAINT_LINE'
      r1 = command.params[0]
      c1 = command.params[1]
      r2 = command.params[2]
      c2 = command.params[3]
      if r1 == r2 # horizontal line
        (c1..c2).each { |y_coord| @temp_matrix[r1, y_coord] = '.' }
      elsif c1 == c2 # vertical line
        (r1..r2).each { |x_coord| @temp_matrix[x_coord, c1] = '.' }
      else
        fail "INVALID coordinates for PAINT_LINE #{r1} #{c1} #{r2} #{c2}' (not a valid line)"
      end
    when 'PAINT_SQUARE'
      raise 'not ready'
    end
  end
end

class Matrix
  def []=(i, j, x)
    @rows[i][j] = x
  end
end

INPUT = './right_angle.in'
matrix = Matrix.rows(File.read(INPUT).split("\n")[1..-1].map { |e| e.split('') })

image = GImage.new(matrix: matrix)
# image.solve
