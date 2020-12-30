package aphine_app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class service {
    public static String alphabet = "абвгдежзийклмнопрстуфхцчшщыьэюя";

    /**
     * метод читання файлу
     *
     * @param fileName - ім'я файлу, що буде прочитано
     * @return строка, в яку прочитано текстовий файл
     * @throws IOException
     */
    public static String readTextFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    /**
     * видалення символів, що не входять в алфавіт шифру
     *
     * @param text - текст, з якого треба видалити символи
     * @return текст без зайвих символів
     */
    public static String deleteOtherSymbols(String text) {
        StringBuilder res = new StringBuilder();
        for (char chr : text.toLowerCase().replace("ё", "е").toCharArray()) {
            if (alphabet.indexOf(chr) > -1) {
                res.append(chr);
            }
        }
        return res.toString();
    }

    /**
     * метод, що повертає HashMap символів та їх номерів у алфавіті
     *
     * @return HashMap<Character, Integer>
     */
    public static HashMap<Character, Integer> charNumbers() {
        HashMap<Character, Integer> res = new HashMap<Character, Integer>();
        for (int i = 0; i < alphabet.length(); i++) {
            res.put(alphabet.charAt(i), i);
        }
        return res;
    }

    /**
     * метод, що повертає HashMap номерів та символів, що їм відповідають у алфавіті
     *
     * @return HashMap<Integer, Character>
     */
    public static HashMap<Integer, Character> numberedAlphabet() {
        HashMap<Integer, Character> res = new HashMap<Integer, Character>();
        for (int i = 0; i < alphabet.length(); i++) {
            res.put(i, alphabet.charAt(i));
        }
        return res;
    }

    /**
     * повертає (перший) ключ за значенням у HashMap
     *
     * @param map   - HashMap
     * @param value - значення, ключ якого треба знайти
     * @return ключ, що відповідає заданному значенню
     */
    public static <K, V> Stream<K> keys(HashMap<K, V> map, V value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(HashMap.Entry::getKey);
    }

    /**
     * сортування LinkedHashMap за значенням значень
     * LinkedHashMap використовується для зберігання порядку елементів та врахування можливості однакових значень
     *
     * @return відсортований LinkedHashMap
     */
    public static LinkedHashMap<String, Integer> sortByValue(LinkedHashMap<String, Integer> map) {
        List<Integer> m_values = new ArrayList<>(map.values());
        Set<Integer> values = new HashSet<>(m_values);
        LinkedHashMap<String, Integer> res = new LinkedHashMap<>();
        List<Integer> sortedvalues = values.stream()
                .sorted(Comparator.reverseOrder())
                .map(Integer.class::cast)
                .collect(Collectors.toList());
        for (Integer i : sortedvalues) {
            for (String key : map.keySet()) {
                if (map.get(key) == i) {
                    res.put(key, i);
                }
            }
        }
        return res;
    }

    /**
     * отримання декартового добутку двох списків
     *
     * @return список списків, кожний з яких складається з двох елементів
     */
    public static <T> ArrayList<ArrayList<T>> cartesianProduct(ArrayList<ArrayList<T>> lists) {
        ArrayList<ArrayList<T>> resultLists = new ArrayList<ArrayList<T>>();
        if (lists.size() == 0) {
            resultLists.add(new ArrayList<T>());
            return resultLists;
        } else {
            ArrayList<T> firstList = lists.get(0);
            ArrayList<ArrayList<T>> remainingLists = cartesianProduct(new ArrayList<>(lists.subList(1, lists.size())));
            for (T condition : firstList) {
                for (ArrayList<T> remainingList : remainingLists) {
                    ArrayList<T> resultList = new ArrayList<T>();
                    resultList.add(condition);
                    resultList.addAll(remainingList);
                    resultLists.add(resultList);
                }
            }
        }
        return resultLists;
    }

    /**
     * @param text - текст, в якому треба порахувати частоту букв
     * @return - True, якщо найбільш частіші буква "о", "е", "и"
     */
    public static boolean letterFreq(String text) {
        LinkedHashMap<String, Integer> counted = new LinkedHashMap<>();

        for (String item : text.split("")) {
            if (counted.containsKey(item)) {
                counted.put(item, counted.get(item) + 1);
            } else {
                counted.put(item, 1);
            }
        }

        counted = sortByValue(counted);
        List<String> topLetters = counted.keySet().stream()
                .limit(3)
                .collect(Collectors.toList());

        return (topLetters.contains("о") & topLetters.contains("е") & topLetters.contains("и"));
    }
}