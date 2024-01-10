class QueryBuilder:
    def __init__(self, table):
        self.table = table
        self.conditions = []

    def where(self, condition):
        self.conditions.append(condition)
        return self

    def and_where(self, condition):
        return self.where(f'AND {condition}')

    def or_where(self, condition):
        return self.where(f'OR {condition}')

    def build(self):
        query = f'SELECT * FROM {self.table}'
        if self.conditions:
            query += ' WHERE ' + ' '.join(self.conditions)
        return query

