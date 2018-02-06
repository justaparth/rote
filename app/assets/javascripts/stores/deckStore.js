/* eslint class-methods-use-this: 0 */

import { ReduceStore } from 'flux/utils';
import Immutable from 'immutable';
import Dispatcher from '../dispatcher';
import ActionTypes from '../constants';

class DeckStore extends ReduceStore {
  constructor() {
    super(Dispatcher);
  }

  getInitialState() {
    const map =
      Immutable.Map().set(1, []).set(2, []).set(3, []);
    return map;
  }

  reduce(state, action) {
    switch (action.actionType) {
      case ActionTypes.CREATE_CARD: {
        const { card } = action;
        const newDeck = state.get(card.deckId);
        newDeck.append(card);
        return state.set(card.deckId, newDeck);
      }
      default: {
        return null;
      }
    }
  }
}

export default new DeckStore();
