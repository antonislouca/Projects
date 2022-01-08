"""
This file contains fucntions that perform the preprocessing stage.
Functiosn that perform the dummy encoding, the conversion of each feature etc.
"""
# @--------------------IMPORTS-------------------------------
from sklearn.feature_extraction.text import TfidfVectorizer
import pandas as pd
import numpy as np
import category_encoders as ce
import matplotlib.pyplot as plt
# from sklearn.preprocessing import OrdinalEncoder


# @---------------------FUNTIONS-----------------------------
# onehot_encoder = ce.OneHotEncoder(cols =['Airline'], handle_unknown='return_nan',return_df=True , use_cat_names=True)
# df_onehot = onehot_encoder.fit_transform (df)
"""Performs fummy encoding on the given column"""


def dummy_encode(dataf, column):
    df_dummy = pd.get_dummies(
        dataf, columns=[column], prefix=column[:4]+"_", prefix_sep='')
    return df_dummy
    # dataf = dataf.drop('Airline', axis=1)
    # dataf.join(df_dummy)


"""converts feature date of journey to correct format
"""


def date_to_timestamp(dataf):
    date = list(dataf['Date_of_Journey'])
    newdate = list()
    for d in date:
        newdate.append(pd.Timestamp(d))
    dataf['Date_of_Journey'] = newdate


"""converts feature date of journey to correct format"""


def change_timestamp_to_day_and_month(dataf):
    date_to_timestamp(dataf)
    dataf['day'] = dataf['Date_of_Journey'].dt.weekday
    dataf['month'] = dataf['Date_of_Journey'].dt.month
    dataf['day_sin'] = np.sin(2 * np.pi * dataf['day']/6.0)
    dataf['day_cos'] = np.cos(2 * np.pi * dataf['day']/6.0)
    dataf['month_sin'] = np.sin(2 * np.pi * dataf['month']/12.0)
    dataf['month_cos'] = np.cos(2 * np.pi * dataf['month']/12.0)
    dataf = dataf.drop(columns=['Date_of_Journey', 'month', 'day'])
    return dataf


"""removes the arrow from the route column"""


def remove_arrow(row):
    route_str = str(row)
    route_list = route_str.split()
    for item in route_list:
        if item == '\u2192':
            route_list.remove(item)
    return route_list


"""converts the duration featur to an integer representing minutes"""


def convert_to_minutes_duration(row):  # assisting function

    if 'm' not in row:  # add padding for minutes
        row = row+" 0m"
    if 'h' not in row:  # add padding for hours
        row = "0h "+row
    # print(row)
    H = str(row).split(" ")

    minutes = int(H[0][:len(H[0])-1])*60+int(H[1][:len(H[1])-1])
    # print(minutes)
    return minutes  # return minutes


"""counts the different kind of stops"""


def count_stops(row):
    return len(row)-2


"""function that performs encoding departure time"""


def change_dep_time(dataf):
    df['Dep_Time'] = df['Dep_Time'].apply(lambda row: change_semicolons(row))
    dataf['Dep_hour_sin'] = np.sin(2 * np.pi * dataf['Dep_Time']/23.59)
    dataf['Dep_hour_cos'] = np.cos(2 * np.pi * dataf['Dep_Time']/23.59)
    dataf = dataf.drop(columns=['Dep_Time'])
    return dataf


def change_semicolons(row):
    row_str = str(row)
    row_str = row_str.replace(":", ".")
    return float(row_str)


"""function that creates ordinal encoding for additional info column"""


def create_ordinal_additional_info(dataf):
    df['Additional_Info'] = df['Additional_Info'].str.lower()
    ordinal_encoder = ce.OrdinalEncoder(cols=['Additional_Info'], return_df=True, mapping=[
        {'col': 'Additional_Info', 'mapping': {'no info': 0, 'in-flight meal not included': 1, 'no check-in baggage included': 2,
                                               'red-eye flight': 3, '1 long layover': 4, '1 short layover': 5, '2 long layover': 6,
                                               'change airports': 7, 'business class': 8}}])
    dataf = ordinal_encoder.fit_transform(dataf)

    return dataf


def check_key(key, mapped_dict):
    return mapped_dict[key]


def remove_arrows(route):
    route = str(route)
    route = route.split(' → ')
    return ' '.join(route)


"""function that performs the TF-idf  score generation for the route feature
creates new features for each different acronym in the route feaure
USED IN route folder only
"""


def route_config(df):

    df['Route'] = df['Route'].apply(remove_arrows)

    tf_idf = TfidfVectorizer(ngram_range=(1, 1), lowercase=False)
    route_new = tf_idf.fit_transform(df['Route'])

    route_new = pd.DataFrame(data=route_new.toarray(),
                             columns=tf_idf.get_feature_names())
    df = pd.concat([df, route_new], axis=1)
    df.drop('Route', axis=1, inplace=True)
    return df


# @----------------MAIN------------------------
df = pd.read_csv(r'Data_Train.csv')

for i in df.columns:
    print("Unique values of feature", i, df[i].nunique())

# convert duration to minutes
df['Duration'] = df['Duration'].apply(
    lambda row: convert_to_minutes_duration(row))

# fix route by removing arrows
df['Route'] = df['Route'].apply(lambda row: remove_arrow(row))
df['Total_Stops'] = df['Route'].apply(lambda row: count_stops(row))
# Encode utf colums with one hot
df = dummy_encode(df, 'Airline')
df = dummy_encode(df, 'Source')
df = dummy_encode(df, 'Destination')

# make cyclical for day and month of date of journey column
df = change_timestamp_to_day_and_month(df)
df = change_dep_time(df)
df = create_ordinal_additional_info(df)

df = route_config(df)
# move prices column to the end
temp = df.pop('Price')
df['Price'] = temp

df.to_csv(r'dataset_after_preprocessing.csv', index=False)
