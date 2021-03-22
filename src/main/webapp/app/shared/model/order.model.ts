export interface IOrder {
  id?: string;
  count?: number;
  subTotal?: number;
  total?: number;
  state?: boolean;
  productId?: string;
  saleId?: string;
}

export const defaultValue: Readonly<IOrder> = {
  state: false,
};
