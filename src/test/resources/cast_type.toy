class User [email]
end

class Person [name]
end

class Student [user_email, person_name]: User [user_email], Person [person_name]
end

student = new Student ["test@email"]

# check email property
assert student :: user_email == "test@email"
assert student as User :: email == "test@email"

# check name property
assert student :: person_name == null
assert student as Person :: name == null

# set student's property used as a reference in user's property
student :: person_name = "Randy"
assert student :: person_name == "Randy"
assert student as Person :: name == "Randy"

# set person's property used as a reference in person's property
student as Person :: name = "Stan"
assert student :: person_name == "Stan"
assert student as Person :: name == "Stan"