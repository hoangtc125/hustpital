import dayjs from 'dayjs';
import { ICountries } from 'app/shared/model/countries.model';
import { ICities } from 'app/shared/model/cities.model';
import { IDistricts } from 'app/shared/model/districts.model';
import { IWards } from 'app/shared/model/wards.model';
import { IEthnics } from 'app/shared/model/ethnics.model';
import { IJobs } from 'app/shared/model/jobs.model';
import { IBhyt } from 'app/shared/model/bhyt.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { BHYT } from 'app/shared/model/enumerations/bhyt.model';

export interface IPatients {
  id?: string;
  name?: string | null;
  gender?: Gender | null;
  address?: string | null;
  dateOfBirth?: string | null;
  phone?: string | null;
  citizenIdentification?: string | null;
  maBHXH?: string | null;
  workPlace?: string | null;
  workAddress?: string | null;
  patientType?: BHYT | null;
  country?: ICountries | null;
  city?: ICities | null;
  district?: IDistricts | null;
  ward?: IWards | null;
  ethnic?: IEthnics | null;
  job?: IJobs | null;
  bHYT?: IBhyt | null;
}

export const defaultValue: Readonly<IPatients> = {};
