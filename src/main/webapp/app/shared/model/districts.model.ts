import { ICities } from 'app/shared/model/cities.model';

export interface IDistricts {
  id?: string;
  name?: string | null;
  city?: ICities;
}

export const defaultValue: Readonly<IDistricts> = {};
