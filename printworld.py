from PIL import Image
from PIL import ImageDraw
from worldmap import *


w = World.random_world(100, 100, 60)


im = Image.new("RGB", (200, 200), "white")
draw = ImageDraw.Draw(im)

for node in w.nodes:
  for neigh in node.neigh:
    draw.line([(node.x + 10, node.y  + 10),
               (neigh.x + 10, neigh.y + 10)], fill=128)
del draw
print "CEVA"

im.save("img.png", "PNG")


