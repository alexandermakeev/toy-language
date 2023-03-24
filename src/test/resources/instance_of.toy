class Animal
end

class Mammal: Animal
end

class Cat: Mammal
end

class Reptile
end

class Lizard: Animal, Reptile
end

cat = new Cat
assert cat is Cat
assert cat is Mammal
assert cat is Animal
assert !(cat is Reptile)
assert !(cat is Lizard)

lizard = new Lizard
assert lizard is Lizard
assert lizard is Reptile
assert lizard is Animal
assert !(lizard is Mammal)
assert !(lizard is Cat)