fun bubble_sort [ arr, n ]
    loop i in 0..n - 1
        loop j in 0..n - i - 1
            if arr{j+1} < arr{j}
                temp = arr{j}
                arr{j} = arr{j + 1}
                arr{j + 1} = temp
            end
       end
    end
end

fun is_sorted [ arr, n ]
    loop i in 0..n - 1
        if arr{i} > arr{i+1}
            return false
        end
    end
    return true
end

arr = {}
arr_len = 20
loop i in 0..arr_len by 1
    arr << i * 117 % 17 - 1
end

assert arr == {-1, 14, 12, 10, 8, 6, 4, 2, 0, 15, 13, 11, 9, 7, 5, 3, 1, -1, 14, 12}
assert ! is_sorted [ arr, arr_len ]

bubble_sort [ arr, arr_len ]
assert arr == {-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 12, 13, 14, 14, 15}
assert is_sorted [ arr, arr_len ]