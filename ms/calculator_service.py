from flask import Flask, request, jsonify

app = Flask(__name__)

required_keys = ['num1', 'num2']

def wrong_format():
    return jsonify({'result': "Bitte nutzen sie das Format: {'num1': 3, 'num2': 5}"})

def check_keys(json_obj, keys):
    print(json_obj)
    for key in keys:
        if key not in json_obj:
            return False
    return True

@app.route('/add', methods=['POST'])
def add():
    data = request.get_json()
    if check_keys(data, required_keys):
        result = data['num1'] + data['num2']
        return jsonify({'result': result})
    else:
        return wrong_format()

@app.route('/subtract', methods=['POST'])
def subtract():
    data = request.get_json()
    if check_keys(data, required_keys):
        result = data['num1'] - data['num2']
        return jsonify({'result': result})
    else:
        return wrong_format()

@app.route('/multiply', methods=['POST'])
def multiply():
    data = request.get_json()
    if check_keys(data, required_keys):
        result = data['num1'] * data['num2']
        return jsonify({'result': result})
    else:
        return wrong_format()

@app.route('/divide', methods=['POST'])
def divide():
    data = request.get_json()
    if data['num2'] == 0:
        return jsonify({'error': 'Division by zero is not allowed'})
    if check_keys(data, required_keys):
        result = data['num1'] / data['num2']
        return jsonify({'result': result})
    else:
        return wrong_format()

if __name__ == '__main__':
    app.run(debug=True)



