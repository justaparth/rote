import { EventEmitter } from 'events';
import { ReduceStore } from 'flux/utils';
import Dispatcher from '../dispatcher';
import ActionTypes from '../constants';

class CardStore extends ReduceStore {

  constructor() {
    super(Dispatcher);
  }

  getInitialState() {
    return {
      cards: []
    }
  }

  areEqual(a, b) {
    false;
  }

  reduce(state, action) {
    console.log(state);
    console.log(action);
    switch (action.actionType) {
      case ActionTypes.CREATE_CARD:
        state.cards.push(action.card);
        return state;
      default:
        return null;
    }
  }

}

export default new CardStore();
