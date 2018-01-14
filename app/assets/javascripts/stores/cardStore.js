import { EventEmitter } from 'events';
import Dispatcher from '../dispatcher';
import ActionTypes from '../constants';

const CHANGE = 'CHANGE';
let _cardStore = [];

class CardStore extends EventEmitter {

  constructor() {
    super();

    // Registers action handler with the Dispatcher.
    Dispatcher.register(this._registerToActions.bind(this));
  }

  // Switches over the action's type when an action is dispatched
  _registerToActions(action) {
    switch(action.actionType) {
      case ActionTypes.CREATE_CARD:
        this._addNewCard(action.payload);
      break;
    }
  }

  _addNewCard(card) {
    card.id = _cardStore.length;
    _cardStore.push(card);
    this.emit(CHANGE);
  }

  getAllItems() {
    return _cardStore;
  }

    // Hooks a React component's callback to the CHANGED event.
  addChangeListener(callback) {
      this.on(CHANGE, callback);
  }

  // Removes the listener from the CHANGED event.
  removeChangeListener(callback) {
      this.removeListener(CHANGE, callback);
  }

}

export default new CardStore();
