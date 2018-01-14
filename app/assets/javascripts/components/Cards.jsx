import React from 'react';
import CardActions from '../actions/cardActions.js'
import CardStore from '../stores/cardStore.js';

class AddCard extends React.Component {

    // Set the initial state.
    constructor(props) {
        super(props);

        this._getFreshItem = this._getFreshItem.bind(this);

        this.state = {
            item: this._getFreshItem()
        };
    }

    // Return a fresh item.
    _getFreshItem() {
        return {
            description: '',
            amount: ''
        };
    }

    // Update the state.
    _updateState(event) {
        let field = event.target.name;
        let value = event.target.value;

        // If the amount is changed and it's not a float, return.
        if (value && field === 'amount' && !value.match(/^[a-z0-9.\+\-]+$/g)) {
            return;
        }

        this.state.item[field] = value;
        this.setState({ item : this.state.item });
    }

    // Add a new item.
    _addNewItem(event) {
      event.preventDefault();
      this.state.item.description = this.state.item.description || '-';
      this.state.item.amount = this.state.item.amount || '0';
      CardActions.addNewItem(this.state.item);
      this.setState({ item : this._getFreshItem() });
    }

    render() {
        return (
            <div>
                <h3 className="total-budget">$0</h3>
                <form className="form-inline add-item" onSubmit={this._addNewItem.bind(this)}>
                    <input type="text" className="form-control description" name="description" value={this.state.item.description} placeholder="Description" onChange={this._updateState.bind(this)} />
                    <div className="input-group amount">
                        <div className="input-group-addon">$</div>
                        <input type="text" className="form-control" name="amount" value={this.state.item.amount} placeholder="Amount" onChange={this._updateState.bind(this)} />
                    </div>
                    <button type="submit" className="btn btn-primary add">Add</button>
                </form>
            </div>
        )
    }
}

class ListCards extends React.Component {

  constructor(props) {
     super(props);
     this.state = {
         items: CardStore.getAllItems()
     };
     this._onChange = this._onChange.bind(this);
  }

  _onChange() {
     this.setState({ items: CardStore.getAllItems() });
  }

  componentWillMount() {
     CardStore.addChangeListener(this._onChange);
  }

  componentWillUnmount() {
     CardStore.removeChangeListener(this._onChange);
  }


    render() {

        let noItemsMessage;

        // Show a friendly message instead if there are no items.
        if (!this.state.items.length) {
            noItemsMessage = (<li className="no-items">Your wallet is new!</li>);
        }

        return (
            <ul className="items-list">
                {noItemsMessage}
                {this.state.items.map((itemDetails) => {
                    let amountType = parseFloat(itemDetails.amount) > 0 ? 'positive' : 'negative';
                    return (<li key={itemDetails.id}>{itemDetails.description} <span className={amountType}>{itemDetails.amount}</span></li>);
                })}
            </ul>
        );
    }
}

class Cards extends React.Component {
  render() {
    return (
      <div>
        <ListCards />
        <AddCard />
      </div>
    )
  }
}

export default Cards;
