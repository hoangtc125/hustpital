import { IDistricts } from 'app/shared/model/districts.model';

export interface IWards {
  id?: string;
  name?: string | null;
  district?: IDistricts;
}

export const defaultValue: Readonly<IWards> = {};
