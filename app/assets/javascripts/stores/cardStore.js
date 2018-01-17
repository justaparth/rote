import { EventEmitter } from 'events';
import { ReduceStore } from 'flux/utils';
import Dispatcher from '../dispatcher';
import ActionTypes from '../constants';
import Immutable from 'immutable';

class CardStore extends ReduceStore {

  constructor() {
    super(Dispatcher);
  }

  getInitialState() {
    return Immutable.List();
  }

  areEqual(a, b) {
    false;
  }

  reduce(state, action) {
    switch (action.actionType) {
      case ActionTypes.CREATE_CARD:
        return state.push(action.card);
      default:
        return null;
    }
  }

}

export default new CardStore();
