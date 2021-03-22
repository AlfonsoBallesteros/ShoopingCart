export interface IProduct {
  id?: string;
  code?: string;
  name?: string;
  stock?: number;
  detail?: string;
  price?: number;
  image?: string;
}

export const defaultValue: Readonly<IProduct> = {};
