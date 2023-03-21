main []

fun main []
    stack = new Stack []
    loop num in 0..5
        # push 0,1,2,3,4
        stack :: push [ num ]
    end

    assert stack :: arr == {0, 1, 2, 3, 4}

    size = stack :: size []
    assert size == 5

    loop i in 0..size
        # should return 4,3,2,1,0
        popped_value = stack :: pop []
        assert popped_value == size - i - 1
    end
end

class Stack []
    arr = {}
    n = 0

    fun push [ item ]
        this :: arr << item
        n = n + 1
    end

    fun pop []
        n = n - 1
        item = arr { n }
        arr { n } = null
        return item
    end

    fun size []
        return this :: n
    end
end