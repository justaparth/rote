/* eslint class-methods-use-this: 0 */

import { ReduceStore } from 'flux/utils';
import Immutable from 'immutable';
import Dispatcher from '../dispatcher';
import ActionTypes from '../constants';

class CardStore extends ReduceStore {
  constructor() {
    super(Dispatcher);
  }

  getInitialState() {
    return Immutable.List();
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
