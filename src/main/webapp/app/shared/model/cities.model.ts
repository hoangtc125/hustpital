import { ICountries } from 'app/shared/model/countries.model';

export interface ICities {
  id?: string;
  name?: string | null;
  country?: ICountries;
}

export const defaultValue: Readonly<ICities> = {};
