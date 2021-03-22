import { Moment } from 'moment';

export interface IStore {
  id?: string;
  date?: string;
  quantity?: number;
  move?: string;
  productId?: string;
}

export const defaultValue: Readonly<IStore> = {};
