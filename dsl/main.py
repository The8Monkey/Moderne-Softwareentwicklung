from QueryBuilder import QueryBuilder

if __name__ == '__main__':
    query = (
        QueryBuilder('people')
        .where('age > 50')
        .and_where('sex = "female"')
        .or_where('kids < 2')
        .build()
    )
    print(query)
