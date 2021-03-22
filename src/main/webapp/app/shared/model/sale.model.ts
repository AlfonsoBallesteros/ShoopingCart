import { Moment } from 'moment';
import { IOrder } from 'app/shared/model/order.model';

export interface ISale {
  id?: string;
  code?: string;
  date?: string;
  medioPago?: string;
  subTotal?: number;
  total?: number;
  state?: boolean;
  orders?: IOrder[];
}

export const defaultValue: Readonly<ISale> = {
  state: false,
};
