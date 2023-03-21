class Add [x, y]
    fun sum
        return x + y
    end
end

class Mul [a, b]
    fun mul
        return a * b
    end
end

class Sub [a, b]
    fun sub
        return a - b
    end
end

class Div [m, n]
    fun div
        return m / n
    end
end

class Exp [m, n]
    fun exp
        return m ** n
    end
end

class Fib [ n ]
    fun fib
        return fib [ n ]
    end

    fun fib [ n ]
        if n < 2
            return n
        end
        return fib [ n - 1 ] + fib [ n - 2 ]
    end
end

class Calculator [p, q]: Add [p, q], Sub [q, p],
                         Mul [p, q], Div [q, p],
                         Exp [p, q], Fib [ q ]
end

calc = new Calculator [2, 10]
assert calc :: sum [] == 12
assert calc :: sub [] == 8
assert calc :: mul [] == 20
assert calc :: div [] == 5
assert calc :: exp [] == 1024
assert calc :: fib [] == 55