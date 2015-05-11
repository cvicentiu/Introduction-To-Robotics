import math
import random

class World(object):
  def __init__(self):
    self.nodes = []

  def add_node(self, node):
    self.nodes.append(node)

  def connect_nodes(self, X, Y):
    for n in X.neigh:
      if n == Y:
        return False
    for A in self.nodes:
      for B in A.neigh:
        if A == X or A == Y or B == X or B == Y:
          continue
        if World.nodes_intersect(A, B, X, Y):
          return False
    X.neigh.append(Y)
    Y.neigh.append(X)
    return True

  def closest_node(self, x, y):
    node_curr = Node(x, y)
    result = self.nodes[0]
    dist = Node.distance(self.nodes[0], node_curr)
    for node in self.nodes:
      curr_dist = Node.distance(node, node_curr)
      if curr_dist < dist:
        dist = curr_dist
        result = node
    return result

  def check_all_nodes_connected(self):
    if len(self.nodes) == 0:
      return True
    queue = []
    queue.append(self.nodes[0])
    visited = set([])
    while len(queue) > 0:
      curr = queue.pop(0)
      for neigh in curr.neigh:
        if neigh in visited:
          continue
        visited.add(neigh)
        queue.append(neigh)
    if len(visited) == len(self.nodes):
      return True
    return False

  def random_connect_nodes(self):
    while not self.check_all_nodes_connected():
      d_thresh = 0
      while True:
        n1 = random.choice(self.nodes)
        n2 = random.choice(self.nodes)
        if Node.distance(n1, n2) > d_thresh:
          d_thresh += 0.01
          continue
        if self.connect_nodes(n1, n2):
          break;


  def __repr__(self):
    return str(len(self.nodes)) + "\n" +  str(self.nodes)

  @staticmethod
  def ccw(A, B, C):
    return (C.y - A.y) * (B.x - A.x) > (B.y - A.y) * (C.x - A.x)

  @staticmethod
  def nodes_intersect(A, B, C, D):
    return World.ccw(A,C,D) != World.ccw(B,C,D) \
            and World.ccw(A,B,C) != World.ccw(A,B,D)

  @staticmethod
  def random_world(lim_x, lim_y, num_nodes):
    w = World()
    for _ in range(num_nodes):
      coord_x = random.randint(0, lim_x)
      coord_y = random.randint(0, lim_y)
      exists = False
      for n in w.nodes:
        if n.x == coord_x and n.y == coord_y:
          exists = True
          break
      if not exists:
        w.add_node(Node(coord_x, coord_y))
    w.random_connect_nodes()
    return w




class Node(object):
  def __init__(self, x, y):
    self.x = x
    self.y = y
    self.neigh = []

  def add_neighbor(self, node):
    self.neigh.append(node)

  @staticmethod
  def distance(node_a, node_b):
    return math.pow(node_a.x - node_b.x, 2) + math.pow(node_a.y - node_b.y, 2)

  def __repr__(self):
    return "N:" + str(self.x) + " " + str(self.y)
