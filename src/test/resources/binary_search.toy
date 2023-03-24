fun binary_search [ arr, n, lo, hi, key ]
    if hi >= lo
        mid = (hi + lo) // 2
        if arr{mid} < key
            return binary_search [ arr, n, mid + 1, hi, key ]
        elif arr{mid} > key
            return binary_search [ arr, n, lo, mid - 1, key ]
        else
            return mid
        end
    else
        return -1
    end
    return
end

arr = {10, 20, 30, 50, 60, 80, 110, 130, 140, 170}
n = 10

assert binary_search [ arr, n, 0, n - 1, 10 ] == 0
assert binary_search [ arr, n, 0, n - 1, 80 ] == 5
assert binary_search [ arr, n, 0, n - 1, 170 ] == 9
assert binary_search [ arr, n, 0, n - 1, 5 ] == -1
assert binary_search [ arr, n, 0, n - 1, 85 ] == -1
assert binary_search [ arr, n, 0, n - 1, 175 ] == -1